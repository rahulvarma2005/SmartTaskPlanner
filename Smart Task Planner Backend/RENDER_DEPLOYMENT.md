# Deploying SmartTaskPlanner Backend to Render

This guide will help you deploy your Spring Boot backend to Render.

## 🚀 Prerequisites

1. A [Render account](https://render.com) (free tier available)
2. Your code pushed to GitHub
3. A Google Gemini API key

## 📋 Step-by-Step Deployment

### Option 1: Using render.yaml (Recommended)

1. **Push your code to GitHub**
   ```bash
   git add .
   git commit -m "Prepare for Render deployment"
   git push origin master
   ```

2. **Create a New Blueprint on Render**
   - Go to [Render Dashboard](https://dashboard.render.com/)
   - Click "New +" → "Blueprint"
   - Connect your GitHub repository
   - Render will automatically detect `render.yaml`

3. **Set Environment Variables**
   In the Render dashboard, set:
   - `GEMINI_API_KEY`: Your Google Gemini API key
   - Database will be automatically created and linked

4. **Deploy**
   - Click "Apply" to start deployment
   - Wait 5-10 minutes for the first build

### Option 2: Manual Setup

#### Step 1: Create PostgreSQL Database

1. Go to Render Dashboard → "New +" → "PostgreSQL"
2. Configure:
   - **Name**: `smarttaskplanner-db`
   - **Database**: `smarttaskplanner`
   - **Plan**: Free
   - **Region**: Choose closest to your users
3. Click "Create Database"
4. Save the **Internal Database URL** (starts with `postgresql://`)

#### Step 2: Create Web Service

1. Go to Render Dashboard → "New +" → "Web Service"
2. Connect your GitHub repository
3. Configure:

   **Basic Settings:**
   - **Name**: `smarttaskplanner-backend`
   - **Region**: Same as database
   - **Branch**: `master`
   - **Root Directory**: `Smart Task Planner Backend`
   - **Runtime**: Docker
   - **Plan**: Free (or Starter for better performance)

   **Docker Settings:**
   - **Dockerfile Path**: `./Dockerfile`

   **Health Check:**
   - **Health Check Path**: `/actuator/health`

4. **Environment Variables** (Add in "Environment" section):
   
   ```
   PORT=10000
   GEMINI_API_KEY=your_gemini_api_key_here
   GEMINI_API_URL=https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent
   DATABASE_URL=[paste your Internal Database URL here]
   JAVA_OPTS=-Xmx512m -Xms256m
   ```

5. Click "Create Web Service"

#### Step 3: Wait for Deployment

- Initial deployment takes 5-10 minutes
- Watch the logs for any errors
- Once deployed, you'll get a URL like: `https://smarttaskplanner-backend.onrender.com`

## ✅ Verify Deployment

1. **Health Check**
   ```bash
   curl https://your-app-name.onrender.com/actuator/health
   ```
   Should return: `{"status":"UP"}`

2. **Test API Endpoint**
   ```bash
   curl -X POST https://your-app-name.onrender.com/api/tasks/generate \
     -H "Content-Type: application/json" \
     -d '{"goal": "Learn Docker in one week"}'
   ```

## 🔧 Configuration Notes

### Free Tier Limitations
- Service spins down after 15 minutes of inactivity
- First request after spin-down takes ~30 seconds
- 750 hours/month free compute time
- Database: 90-day data retention, then deleted

### Environment Variables Required
| Variable | Description | Required |
|----------|-------------|----------|
| `PORT` | Port number (Render sets automatically) | ✅ |
| `DATABASE_URL` | PostgreSQL connection string | ✅ |
| `GEMINI_API_KEY` | Google Gemini API key | ✅ |
| `GEMINI_API_URL` | Gemini API endpoint | ✅ |
| `JAVA_OPTS` | JVM options for memory management | ⭐ Recommended |

### Port Configuration
- Render automatically assigns a port via the `PORT` environment variable
- Your `application.properties` is configured to use: `server.port=${PORT:8081}`
- This means: use `$PORT` if set, otherwise default to 8081

### Database Configuration
- Render provides a PostgreSQL database with the free tier
- Connection string format: `postgresql://user:password@host:port/database`
- Spring Boot automatically parses this when using `${DATABASE_URL}`

## 🐛 Troubleshooting

### Build Fails
1. Check build logs in Render dashboard
2. Ensure Java 21 is being used
3. Verify `mvnw` has execute permissions (already handled in Dockerfile)

### Health Check Fails
1. Verify Spring Boot Actuator is added to `pom.xml`
2. Check if app is listening on correct port
3. Review application logs

### Database Connection Issues
1. Verify `DATABASE_URL` environment variable is set
2. Check if database is in the same region as web service
3. Ensure PostgreSQL database is running

### Memory Issues
If app crashes with OutOfMemoryError:
1. Upgrade to Starter plan (512MB RAM)
2. Optimize `JAVA_OPTS`: `-Xmx400m -Xms200m`
3. Reduce Hibernate connection pool size

## 🔄 Continuous Deployment

Render automatically deploys when you push to your connected branch:

```bash
git add .
git commit -m "Update feature"
git push origin master
```

## 📊 Monitoring

Access your Render dashboard to:
- View deployment logs
- Monitor resource usage
- Check health status
- Review metrics

## 🔐 Security Best Practices

1. **Never commit sensitive data**
   - Keep API keys in environment variables
   - Use `.gitignore` for `application.properties`

2. **Use Environment Variables**
   - All secrets should be in Render dashboard
   - Never hardcode credentials

3. **Enable CORS properly**
   - Configure allowed origins for your frontend
   - Don't use `*` in production

## 💰 Cost Optimization

**Free Tier:**
- Backend: Free (spins down after 15 min)
- Database: Free (90-day retention)

**Paid Tier (if needed):**
- Starter: $7/month (no spin down, 512MB RAM)
- Database: $7/month (persistent, daily backups)

## 📝 Next Steps

1. Deploy frontend to Vercel or Render
2. Update frontend API URL to point to Render backend
3. Set up custom domain (optional)
4. Configure CORS for frontend domain
5. Set up monitoring and alerts

## 🆘 Support

If you encounter issues:
1. Check [Render Documentation](https://render.com/docs)
2. Review [Render Community](https://community.render.com/)
3. Check application logs in Render dashboard

---

**Your backend is now ready for deployment! 🎉**
